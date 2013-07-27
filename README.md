<a href="https://play.google.com/store/apps/details?id=com.lonepulse.travisjr">
  <img alt="Travis Jr. Device Art" src="https://raw.github.com/sahan/Travis-Jr/master/device_art.png" />
</a>
<br>

> **Travis Jr.** /'travis'jC<n-yIr/ <em>prop. noun.</em> **1** A mobile client for [Travis-CI](http://travis-ci.org). 
[![Build Status](https://travis-ci.org/sahan/Travis-Jr.png?branch=master)](https://travis-ci.org/sahan/Travis-Jr) 
[![Stories in Ready](https://badge.waffle.io/sahan/Packrat.png)](http://waffle.io/sahan/Packrat)

<br/>
##About

**Travis Jr.** is a mobile client for the popular continuous integration system [Travis-CI](http://travis-ci.org). 
This application is currently in its infancy with only the bare essential set of features such as following a 
repository status and viewing all recent builds.   

<br/>
##Setup

[Download](https://github.com/sahan/Travis-Jr/archive/master.zip) the repository or clone it and perform an 
`mvn package` to build the project.

```bash
$ git clone git://github.com/sahan/Travis-Jr.git
$ cd Travis-Jr
$ mvn clean package
```

After the APK is generated, change directory to the application module and deploy the APK to a live emulator 
or a connected device.

```bash
$ cd travisjr
$ mvn android:deploy
```

If you use [Eclipse](http://www.eclipse.org/downloads), the project can be imported as an **existing project** 
along with the metadata containing the Maven nature.

For more information on building Android projects using Maven here's [Chapter 14](http://www.sonatype.com/books/mvnref-book/reference/android-dev.html) of `Maven: The Complete Reference`.   

<br/>
##Changelog

* Release 0.1.1 (beta)

	1. Authenticate (member/organization) via GitHub.
	2. View repositories (created/contributed) under CI.
	3. View builds for each repository.
	4. Sign off to switch user.   

<br/>
##Mailing Lists
Find the Travis Jr. mailing list at [ost.io/@sahan/Travis-Jr](http://ost.io/@sahan/Travis-Jr).   

<br/>
##License
This application is licensed under [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
