/*
 * Copyright (C) 2020  Edward Samson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ph.samson.xdg.basedir

import org.scalatest.flatspec.AnyFlatSpec

class DataResolverSpec extends AnyFlatSpec {

  behavior of "DataResolver"

  it can "get top level data dir" in {
    val resolver = data("test1")
    val resolved = resolver.get
    assert(
      resolved.toString().endsWith("home/local/share/test1")
        && resolved.isDirectory
    )
  }

  it can "get file under data dir" in {
    val resolver = data("test1")
    val resolved = resolver.get("data1")
    assert(
      resolved.toString().endsWith("home/local/share/test1/data1")
        && resolved.contentAsString == "data content 1"
    )
  }

  it can "get file under data sub dir" in {
    val resolver = data("test1")
    val resolved = resolver.get("sub1", "data2")
    assert(
      resolved.toString().endsWith("home/local/share/test1/sub1/data2")
        && resolved.contentAsString == "data sub content 1"
    )
  }

  it can "lookup top level data dirs" in {
    val resolver = data("test1")
    val resolved = resolver.lookup
    assert(resolved.size == 3 && resolved.forall(_.isDirectory))
  }

  it can "lookup files under data dirs" in {
    val resolver = data("test1")
    val resolved = resolver.lookup("data1")
    assert(
      resolved.map(_.contentAsString) == Seq(
        "data content 1",
        "data content 2",
        "data content 3"
      )
    )
  }

  it can "lookup files under data sub dirs" in {
    val resolver = data("test1")
    val resolved = resolver.lookup("sub1", "data2")
    assert(
      resolved.map(_.contentAsString) == Seq(
        "data sub content 1",
        "data sub content 2",
        "data sub content 3"
      )
    )
  }

  it should "skip lookup files that do not exist" in {
    val resolver = data("test1")
    val resolved = resolver.lookup("lookup1")
    assert(
      resolved.map(_.contentAsString) == Seq("lookup data 1", "lookup data 2")
    )
  }
}
