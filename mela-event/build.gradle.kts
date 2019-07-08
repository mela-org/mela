dependencies {
  api(Deps.guice)
  implementation(Deps.guava)

  testImplementation(Deps.Test.assertj)
  testImplementation(Deps.Test.JUnit.api)
  testRuntimeOnly(Deps.Test.JUnit.engine)
}