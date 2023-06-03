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

class ConfigResolverSpec extends AnyFlatSpec {

  behavior of "ConfigResolver"

  it can "get top level config dir" in {
    val resolver = config("test1")
    val resolved = resolver.get
    assert(
      resolved.toString().endsWith("home/config/test1") && resolved.isDirectory
    )
  }

  it can "get file under config dir" in {
    val resolver = config("test1")
    val resolved = resolver.get("config1")
    assert(
      resolved.toString().endsWith("home/config/test1/config1")
        && resolved.contentAsString == "config content 1"
    )
  }

  it can "get file under config sub dir" in {
    val resolver = config("test1")
    val resolved = resolver.get("sub1", "config2")
    assert(
      resolved.toString().endsWith("home/config/test1/sub1/config2")
        && resolved.contentAsString == "config sub content 1"
    )
  }

  it can "lookup top level config dirs" in {
    val resolver = config("test1")
    val resolved = resolver.lookup
    assert(resolved.size == 3 && resolved.forall(_.isDirectory))
  }

  it can "lookup files under config dirs" in {
    val resolver = config("test1")
    val resolved = resolver.lookup("config1")
    assert(
      resolved.map(_.contentAsString) == Seq(
        "config content 1",
        "config content 2",
        "config content 3"
      )
    )
  }

  it can "lookup files under config sub dirs" in {
    val resolver = config("test1")
    val resolved = resolver.lookup("sub1", "config2")
    assert(
      resolved.map(_.contentAsString) == Seq(
        "config sub content 1",
        "config sub content 2",
        "config sub content 3"
      )
    )
  }

  it should "skip lookup files that do not exist" in {
    val resolver = config("test1")
    val resolved = resolver.lookup("lookup1")
    assert(
      resolved.map(_.contentAsString) ==
        Seq(
          "lookup config 1",
          "lookup config 2"
        )
    )
  }
}
