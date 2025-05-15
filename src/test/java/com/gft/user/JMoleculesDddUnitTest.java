package com.gft.user;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.ValueObject;
import org.junit.jupiter.api.Test;

@AnalyzeClasses(packages = "com.gft.user")
class JMoleculesDddUnitTest {

    @Test
    void aggregateRootsShouldResideInDomainPackage() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.gft.user");

        ArchRuleDefinition.classes()
                .that().areAnnotatedWith(AggregateRoot.class)
                .should().resideInAPackage("..domain..")
                .check(classes);
    }

    @Test
    void valueObjectsShouldResideInDomainPackage() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.gft.user");

        ArchRuleDefinition.classes()
                .that().areAnnotatedWith(ValueObject.class)
                .should().resideInAPackage("..domain..")
                .check(classes);
    }
}
