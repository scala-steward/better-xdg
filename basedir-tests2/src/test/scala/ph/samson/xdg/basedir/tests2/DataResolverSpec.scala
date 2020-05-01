package ph.samson.xdg.basedir
package tests2

import org.scalatest.flatspec.AnyFlatSpec

class DataResolverSpec extends AnyFlatSpec {

  behavior of "DataResolver"

  it can "get top level data dir" in {
    val resolver = data("test1")
    val resolved = resolver.get
    assert(resolved.toString().endsWith("home/local/share/test1"))
  }

  it can "get file under data dir" in {
    val resolver = data("test1")
    val resolved = resolver.get("data1")
    assert(resolved.toString().endsWith("home/local/share/test1/data1"))
  }

  it can "get file under data sub dir" in {
    val resolver = data("test1")
    val resolved = resolver.get("sub1", "data2")
    assert(resolved.toString().endsWith("home/local/share/test1/sub1/data2"))
  }
}
