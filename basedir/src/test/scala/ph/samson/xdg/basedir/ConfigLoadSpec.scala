package ph.samson.xdg.basedir

import org.scalatest.flatspec.AnyFlatSpec

class ConfigLoadSpec extends AnyFlatSpec {

  behavior of "ConfigResolver.load"

  it should "first, load from system properties" in {
    val resolver = config("test1")
    val resolved = resolver.load
    assert(resolved.getString("test.conf1") == "sysprops")
  }

  it should "second, load from application.conf in classpath" in {
    val resolver = config("test1")
    val resolved = resolver.load
    assert(resolved.getString("test.conf2") == "classpath application.conf")
  }

  it should "third, load from application.conf in config home" in {
    val resolver = config("test1")
    val resolved = resolver.load
    assert(resolved.getString("test.conf3") == "home application.conf")
  }

  it should "fourth, load from application.conf in first config dir" in {
    val resolver = config("test1")
    val resolved = resolver.load
    assert(resolved.getString("test.conf4") == "etc application.conf")
  }

  it should "fifth, load from application.conf in second config dir" in {
    val resolver = config("test1")
    val resolved = resolver.load
    assert(resolved.getString("test.conf5") == "xdg application.conf")
  }

  it should "last, load from reference.conf in classpath" in {
    val resolver = config("test1")
    val resolved = resolver.load
    assert(resolved.getString("test.conf6") == "reference")
  }

  behavior of """ConfigResolver.load("other.conf")"""

  it should "first, load from system properties" in {
    val resolver = config("test1")
    val resolved = resolver.load("other.conf")
    assert(resolved.getString("test.conf1") == "sysprops")
  }

  it should "second, load from other.conf in config home" in {
    val resolver = config("test1")
    val resolved = resolver.load("other.conf")
    assert(resolved.getString("test.conf2") == "home other.conf")
  }

  it should "third, load from other.conf in first config dir" in {
    val resolver = config("test1")
    val resolved = resolver.load("other.conf")
    assert(resolved.getString("test.conf3") == "etc other.conf")
  }

  it should "fourth, load from other.conf in second config dir" in {
    val resolver = config("test1")
    val resolved = resolver.load("other.conf")
    assert(resolved.getString("test.conf4") == "xdg other.conf")
  }

  it should "last, load from reference.conf in classpath" in {
    val resolver = config("test1")
    val resolved = resolver.load("other.conf")
    assert(resolved.getString("test.conf5") == "reference")
  }
}
