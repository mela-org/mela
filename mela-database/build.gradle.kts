dependencies {
  compile(Deps.reactor_core)
  compile(Deps.guava)

  testCompile(Deps.Test.assertj)
  testCompile(Deps.Test.JUnit.api)
  testRuntimeOnly(Deps.Test.JUnit.engine)
}