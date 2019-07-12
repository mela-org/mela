dependencies {
  api(Deps.guice)
  implementation(Deps.guava)
  implementation(Deps.Jackson.databind)
  implementation(Deps.Jackson.Dataformat.yaml)

  testImplementation(Deps.Test.assertj)
  testImplementation(Deps.Test.JUnit.api)
}