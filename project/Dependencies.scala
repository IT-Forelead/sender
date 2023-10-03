import sbt.*

object Dependencies {
  object Versions {
    lazy val circe = "0.14.1"
    lazy val http4s = "0.23.10"
    lazy val refined = "0.10.2"
    lazy val cats = "2.9.0"
    lazy val `cats-effect` = "3.4.8"
    lazy val logback = "1.4.7"
    lazy val log4cats = "2.5.0"
    lazy val pureconfig = "0.17.2"
    lazy val sttp = "3.7.2"
  }
  trait LibGroup {
    def all: Seq[ModuleID]
  }
  object com {
    object github {
      object pureconfig extends LibGroup {
        private def repo(artifact: String): ModuleID =
          "com.github.pureconfig" %% artifact % Versions.pureconfig

        lazy val core: ModuleID = repo("pureconfig")
        lazy val enumeratum: ModuleID = repo("pureconfig-enumeratum")

        override def all: Seq[ModuleID] = Seq(core, enumeratum)
      }
    }
    object softwaremill {
      object sttp extends LibGroup {
        private def sttp(artifact: String): ModuleID =
          "com.softwaremill.sttp.client3" %% artifact % Versions.sttp

        lazy val circe: ModuleID = sttp("circe")
        lazy val `fs2-backend`: ModuleID = sttp("async-http-client-backend-fs2")
        override def all: Seq[ModuleID] = Seq(circe, `fs2-backend`)
      }
    }
  }
  object io {
    object circe extends LibGroup {
      private def circe(artifact: String): ModuleID =
        "io.circe" %% s"circe-$artifact" % Versions.circe

      lazy val core: ModuleID = circe("core")
      lazy val generic: ModuleID = circe("generic")
      lazy val parser: ModuleID = circe("parser")
      lazy val refined: ModuleID = circe("refined")
      lazy val optics: ModuleID = circe("optics")
      lazy val `generic-extras`: ModuleID = circe("generic-extras")
      override def all: Seq[ModuleID] =
        Seq(core, generic, parser, refined, optics, `generic-extras`)
    }
  }
  object org {
    object typelevel {
      object cats {
        lazy val core = "org.typelevel"   %% "cats-core"   % Versions.cats
        lazy val effect = "org.typelevel" %% "cats-effect" % Versions.`cats-effect`
      }
    }
    object http4s extends LibGroup {
      private def http4s(artifact: String): ModuleID =
        "org.http4s" %% s"http4s-$artifact" % Versions.http4s

      lazy val dsl = http4s("dsl")
      lazy val server = http4s("ember-server")
      lazy val client = http4s("ember-client")
      lazy val circe = http4s("circe")
      lazy val `blaze-server` = http4s("blaze-server")
      override def all: Seq[ModuleID] = Seq(dsl, server, client, circe)
    }
  }
  object eu {
    object timepit {
      object refined extends LibGroup {
        private def refined(artifact: String): ModuleID =
          "eu.timepit" %% artifact % Versions.refined

        lazy val core = refined("refined")
        lazy val cats = refined("refined-cats")
        lazy val pureconfig: ModuleID = refined("refined-pureconfig")

        override def all: Seq[ModuleID] = Seq(core, cats, pureconfig)
      }
    }
  }

  object ch {
    object qos {
      lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback
    }
  }
}
