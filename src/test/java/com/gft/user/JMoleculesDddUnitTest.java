package com.gft.user;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.jmolecules.archunit.JMoleculesDddRules;

@AnalyzeClasses(packages = "com.gft.user")
class JMoleculesDddUnitTest {

    @ArchTest
    void whenCheckingAllClasses_thenCodeFollowsAllDddPrinciples(JavaClasses classes) {
        JMoleculesDddRules.all().check(classes);
    }
}
