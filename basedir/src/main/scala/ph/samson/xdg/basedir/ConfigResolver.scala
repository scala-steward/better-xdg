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
import com.typesafe.config.{Config, ConfigFactory}

class ConfigResolver(base: String) {

  private val configHome =
    sys.env.get(Env.ConfigHome).map(File(_)).getOrElse(Default.ConfigHome)

  private val configDirs: Seq[File] = sys.env
    .get(Env.ConfigDirs)
    .filter(_.nonEmpty)
    .map(dirs => dirs.split(pathSeparatorChar).map(File(_)).toSeq)
    .getOrElse(Default.ConfigDirs)

  def get: File = File(configHome, base)

  def get(path: String, fragments: String*): File =
    File(get, path, fragments: _*)

  def lookup: Seq[File] =
    (configHome +: configDirs).map(dir => File(dir, base)).filter(_.exists)

  def lookup(path: String, fragments: String*): Seq[File] =
    lookup.map(dir => File(dir, path, fragments: _*)).filter(_.exists)

  def load(path: String, fragments: String*): Config = {

    @scala.annotation.tailrec
    def layer(files: List[File], layered: Config): Config = {
      files match {
        case Nil => ConfigFactory.load(layered)
        case head :: next =>
          layer(
            next,
            layered.withFallback(ConfigFactory.parseFile(head.toJava))
          )
      }
    }

    layer(lookup(path, fragments: _*).toList, ConfigFactory.empty())
  }

  def load: Config = {
    ConfigFactory.load(
      ConfigFactory.defaultApplication().withFallback(load("application.conf"))
    )
  }
}
