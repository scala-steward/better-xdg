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

/** Environment variable names */
object Env {

  /** The base directory relative to which user specific data files should be
    * stored.
    */
  val DataHome = "XDG_DATA_HOME"

  /** The base directory relative to which user specific configuration files
    * should be stored.
    */
  val ConfigHome = "XDG_CONFIG_HOME"

  /** The preference-ordered set of base directories to search for data files in
    * addition to the [[DataHome]] base directory. The directories in
    * [[DataDirs]] should be seperated with a colon ':'.
    */
  val DataDirs = "XDG_DATA_DIRS"

  /** The preference-ordered set of base directories to search for configuration
    * files in addition to the [[ConfigHome]] base directory. The directories in
    * [[ConfigDirs]] should be seperated with a colon ':'.
    */
  val ConfigDirs = "XDG_CONFIG_DIRS"

  /** The base directory relative to which user specific non-essential data
    * files should be stored.
    */
  val CacheHome = "XDG_CACHE_HOME"

  /** The base directory relative to which user-specific non-essential runtime
    * files and other file objects (such as sockets, named pipes, ...) should be
    * stored.
    */
  val RuntimeDir = "XDG_RUNTIME_DIR"
}
