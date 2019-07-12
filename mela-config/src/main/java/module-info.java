module com.github.stupremee.mela.config {
  requires com.fasterxml.jackson.databind;
  requires com.google.guice;

  exports com.github.stupremee.mela.config.annotations;
  exports com.github.stupremee.mela.config.json;
  exports com.github.stupremee.mela.config.yaml;
  exports com.github.stupremee.mela.config;

  opens com.github.stupremee.mela.config.jackson;
  opens com.github.stupremee.mela.config.inject;
}