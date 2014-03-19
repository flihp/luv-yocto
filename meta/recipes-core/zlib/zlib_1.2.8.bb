SUMMARY = "Zlib Compression Library"
DESCRIPTION = "Zlib is a general-purpose, patent-free, lossless data compression \
library which is used by many different programs."
HOMEPAGE = "http://zlib.net/"
SECTION = "libs"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://zlib.h;beginline=4;endline=23;md5=fde612df1e5933c428b73844a0c494fd"

PR = "r0"

SRC_URI = "http://www.zlib.net/${BPN}-${PV}.tar.xz \
           file://remove.ldconfig.call.patch \
           "

SRC_URI[md5sum] = "28f1205d8dd2001f26fec1e8c2cebe37"
SRC_URI[sha256sum] = "831df043236df8e9a7667b9e3bb37e1fcb1220a0f163b6de2626774b9590d057"

do_configure (){
	./configure --prefix=${prefix} --shared --libdir=${libdir}
}

do_compile (){
	oe_runmake
}

do_install() {
	oe_runmake DESTDIR=${D} install
}

# We move zlib shared libraries for target builds to avoid
# qa warnings.
#
do_install_append_class-target() {
	if [ ${base_libdir} != ${libdir} ]
	then
		mkdir -p ${D}/${base_libdir}
		mv ${D}/${libdir}/libz.so.* ${D}/${base_libdir}
		tmp=`readlink ${D}/${libdir}/libz.so`
		ln -sf ../../${base_libdir}/$tmp ${D}/${libdir}/libz.so
	fi
}

BBCLASSEXTEND = "native nativesdk"
