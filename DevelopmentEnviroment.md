# Create the recommended development enviroment #

## Requirements ##

### For the android application ###
  1. Eclipse
  1. [Android SDK](http://developer.android.com/sdk/installing.html)
  1. [Mercurial Eclipse plugin](http://www.javaforge.com/project/HGE)

### For the server side application ###
  1. [Google app engine + plug in for Eclipse](http://code.google.com/appengine/downloads.html)

## Instructions ##
  1. Create a new workspace for your projects
  1. Set up your android SDK path (window->android)
  1. Open the AVD Manger and create a new Google API 2.2 device
  1. In the new workspace: Import->Mercurial->clone existing Mercurial repository
    1. clone the default repository: https://realgraffiti.googlecode.com/hg/
    1. clone the common repository: https://common.realgraffiti.googlecode.com/hg/
  1. Fix the RealGraffiti project android configurations (project->android-> choose Google API 2.2)

### For the server side application ###
  1. clone https://common.realgraffiti.googlecode.com/hg/
  1. Download [Google app eninge SDK for Java](http://code.google.com/appengine/downloads.html), and the [Eclipse plug in](http://code.google.com/eclipse/docs/download.html).
  1. Configure the Google app engine location in eclipse (project-> properties-> Google->App Engine