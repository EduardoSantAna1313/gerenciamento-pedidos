package br.com.edu.layer

import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.ArchRule
import com.tngtech.archunit.library.Architectures

@AnalyzeClasses(packages = ["br.com.edu.order"], importOptions = [ImportOption.DoNotIncludeTests::class])
object LayerArchitectureTest {

    @ArchTest
    val rule: ArchRule =

        Architectures.layeredArchitecture().consideringOnlyDependenciesInLayers()
            .layer("domain").definedBy("..domain..")
            .layer("port").definedBy("..ports..")
            .layer("service").definedBy("..service..")
            .layer("adapter").definedBy("..adapters..")
            .layer("config").definedBy("..config..")
            .layer("controller").definedBy("..infra.rest.v1..")

            // only be accessed by
            .whereLayer("config").mayNotAccessAnyLayer()

            // only access
            .whereLayer("domain").mayOnlyAccessLayers("domain")
            .whereLayer("port").mayOnlyAccessLayers("domain", "port")
            .whereLayer("service").mayOnlyAccessLayers("domain", "port", "service")
            .whereLayer("adapter").mayOnlyAccessLayers("domain", "port", "service", "adapter")

}