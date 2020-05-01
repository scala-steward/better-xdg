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

import java.io.File.pathSeparatorChar

import better.files.File

class DataResolver(base: String) {

  private val dataHome =
    sys.env.get(Env.DataHome).map(File(_)).getOrElse(Default.DataHome)

  private val dataDirs: Seq[File] = sys.env
    .get(Env.DataDirs)
    .filter(_.nonEmpty)
    .map(dirs => dirs.split(pathSeparatorChar).map(File(_)).toSeq)
    .getOrElse(Default.DataDirs)

  def get: File = dataHome / base

  def get(path: String, fragments: String*): File =
    resolve(get / path, fragments)

  def lookup: Seq[File] =
    (dataHome +: dataDirs).map(dir => File(dir, base)).filter(_.exists)

  def lookup(path: String, fragments: String*): Seq[File] =
    lookup.map(dir => File(dir, path, fragments: _*)).filter(_.exists)
}
