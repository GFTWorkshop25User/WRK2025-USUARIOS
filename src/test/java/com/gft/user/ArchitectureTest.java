package com.gft.user;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.jmolecules.archunit.JMoleculesArchitectureRules;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(packagesOf = WorkshopApplication.class, importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    private final JavaClasses classes = new ClassFileImporter()
            .importPackages("com.gft.user");

    @Test
    void thereAreNoSystemOutPrintln() {
        ArchRule rule = noClasses()
                .should().callMethod(System.class, "out")
                .orShould().callMethod(System.class, "println")
                .because("Usa un sistema de logging (SLF4J, Log4j) en lugar de System.out.println()");

        rule.check(classes);
    }

    @ArchTest
    ArchRule ControllerNaming = classes()
            .that().areAnnotatedWith(RestController.class)
            .or().haveSimpleNameEndingWith("Controller")
            .should().beAnnotatedWith(RestController.class)
            .andShould().haveSimpleNameEndingWith("Controller")
            .because("controller should be easy to find");

    @ArchTest
    ArchRule RepositoryNaming = classes()
            .that().areAnnotatedWith(Repository.class)
            .should().haveSimpleNameEndingWith("Repository")
            .because("repository should be easy to find");

    @ArchTest
    ArchRule jMoleculesLayers = JMoleculesArchitectureRules.ensureLayering();
}
