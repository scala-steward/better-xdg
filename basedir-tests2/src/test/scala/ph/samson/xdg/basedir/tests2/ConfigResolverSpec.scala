package ph.samson.xdg.basedir
package tests2

import org.scalatest.flatspec.AnyFlatSpec

class ConfigResolverSpec extends AnyFlatSpec {

  it can "get top level config dir" in {
    val resolver = config("test1")
    val resolved = resolver.get
    assert(resolved.toString().endsWith("home/config/test1"))
  }

  it can "get file under config dir" in {
    val resolver = config("test1")
    val resolved = resolver.get("config1")
    assert(resolved.toString().endsWith("home/config/test1/config1"))
  }

  it can "get file under config sub dir" in {
    val resolver = config("test1")
    val resolved = resolver.get("sub1", "config2")
    assert(resolved.toString().endsWith("home/config/test1/sub1/config2"))
  }
}
