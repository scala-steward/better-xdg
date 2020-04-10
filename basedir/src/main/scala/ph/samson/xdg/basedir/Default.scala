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

import better.files._
import File._

object Default {

  /** If [[Env.DataHome]] is either not set or empty, a default equal to
    * [[DataHome]] should be used.
    */
  val DataHome: File = home / ".local" / ".share"

  /** If [[Env.ConfigHome]] is either not set or empty, a default equal to
    * [[ConfigHome]] should be used.
    */
  val ConfigHome: File = home / ".config"

  /** If [[Env.DataDirs]] is either not set or empty, a value equal to
    * [[DataDirs]] should be used.
    */
  val DataDirs: Seq[File] = Seq(
    root / "usr" / "local" / "share",
    root / "usr" / "share"
  )

  /** If [[Env.ConfigDirs]] is either not set or empty, a value equal to
    * [[ConfigDirs]] should be used.
    */
  val ConfigDirs: Seq[File] = Seq(root / "etc" / "xdg")

  /** If [[Env.CacheHome]] is either not set or empty, a default equal to
    * [[CacheHome]] should be used.
    */
  val CacheHome: File = home / ".cache"
}
