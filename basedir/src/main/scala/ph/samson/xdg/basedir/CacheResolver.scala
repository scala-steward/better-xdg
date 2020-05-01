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

import better.files.File

class CacheResolver(base: String) {

  private val cacheHome =
    sys.env.get(Env.CacheHome).map(File(_)).getOrElse(Default.CacheHome)

  def get: File = cacheHome / base

  def get(path: String, fragments: String*): File =
    resolve(get / path, fragments)
}
