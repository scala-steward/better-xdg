ThisBuild / organization := "ph.samson.xdg"
ThisBuild / organizationName := "Edward Samson"
ThisBuild / startYear := Some(2020)
ThisBuild / licenses += "GPL-3.0-or-later" -> url(
  "https://spdx.org/licenses/GPL-3.0-or-later.html"
)
ThisBuild / homepage := Some(url("https://github.com/esamson/better-xdg"))
ThisBuild / developers := List(
  Developer(
    "esamson",
    "Edward Samson",
    "edward@samson.ph",
    url("https://edward.samson.ph")
  )
)

ThisBuild / sonatypeProfileName := "ph.samson"
