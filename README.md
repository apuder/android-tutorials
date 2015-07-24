
Android Tutorials
=================

This repository hosts the source code for some (hopefully) useful
Android tutorials. The [official website][Android Tutorials] hosts
an easy to browse version of the tutorials. The ant-script that is part of
this repository allows to re-generated the web page.

Cloning the Repository
----------------------

Clone the tutorial sources via git:

``
git clone git@github.com:apuder/android-tutorials.git
``


Generating the Web Page
-----------------------

Run ``ant`` in the root directory. The web page will be generated under
``dist/www``. The web page needs to be loaded via a server. On localhost
this can be accomplished via:

```
ant
cd dist/www
python -m SimpleHTTPServer
```

Then open your favorite browser and visit localhost:8000


Contribute
----------

Please consider contributing fixes and/or new tutorials. Add new
tutorials under the directory ``android``. Add an entry for the new
tutorial in ``doc/overview.xml`` and create a Pull Request!


Links
-----

* [Android Tutorials][Android Tutorials]

[Android Tutorials]:http://android-tutorials.info/
