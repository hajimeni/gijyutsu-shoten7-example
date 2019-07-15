package features

import org.scalatest.{FeatureSpec, GivenWhenThen, MustMatchers, OptionValues, Tag}
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.{HtmlUnitFactory, OneBrowserPerSuite, WsScalaTestClient}

abstract class FeatureSpecBase extends FeatureSpec
  with MustMatchers
  with OptionValues
  with WsScalaTestClient
  with GivenWhenThen
  with GuiceOneServerPerSuite
  with OneBrowserPerSuite
  with HtmlUnitFactory {
}

object featureTag extends Tag("feature")