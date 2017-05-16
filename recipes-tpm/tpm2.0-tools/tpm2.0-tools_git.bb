SUMMARY = "Tools for TPM2."
DESCRIPTION = "tpm2.0-tools"
SECTION = "tpm"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=91b7c548d73ea16537799e8060cea819"
DEPENDS += "tpm2.0-tss tpm2-abrmd openssl curl autoconf-archive pkgconfig"
RDEPENDS_${PN} += "libtss2 libtctidevice"
SRC_URI = " \
    git://github.com/01org/tpm2.0-tools.git;protocol=git;branch=master;name=tpm2.0-tools;destsuffix=tpm2.0-tools \
    file://0001-Add-support-for-the-tabrmd-TCTI-module.patch \
    file://0001-tpm2-tools-use-dynamic-linkage-with-tpm2-abrmd.patch \
"

S = "${WORKDIR}/tpm2.0-tools"
# https://lists.yoctoproject.org/pipermail/yocto/2013-November/017042.html
SRCREV = "d8b0ac5b97f357db8cbd26dd237a794d5758e889"
PVBASE := "${PV}"
PV = "${PVBASE}.${SRCPV}"

inherit autotools

EXTRA_OECONF += " \
    --with-tcti-device \
    --without-tcti-socket \
    --with-tcti-tabrmd \
"

SRC_URI += " \
    file://ax_check_compile_flag.m4 \
    file://ax_check_preproc_flag.m4 \
    file://ax_check_link_flag.m4 \
"
do_configure_prepend () {
	mkdir -p ${S}/m4
	cp ${WORKDIR}/ax_check_compile_flag.m4 ${S}/m4
	cp ${WORKDIR}/ax_check_preproc_flag.m4 ${S}/m4
	cp ${WORKDIR}/ax_check_link_flag.m4 ${S}/m4
	# execute the bootstrap script
	currentdir=$(pwd)
	cd ${S}
	ACLOCAL="aclocal --system-acdir=${STAGING_DATADIR}/aclocal" ./bootstrap
	cd ${currentdir}
}
