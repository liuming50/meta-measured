SUMMARY = "Tools for TPM2."
DESCRIPTION = "tpm2.0-tools"
SECTION = "tpm"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=91b7c548d73ea16537799e8060cea819"
DEPENDS += "tpm2.0-tss openssl curl"
SRC_URI = "git://github.com/01org/tpm2.0-tools.git;protocol=git;branch=master;name=tpm2.0-tools;destsuffix=tpm2.0-tools"

S = "${WORKDIR}/tpm2.0-tools"
# https://lists.yoctoproject.org/pipermail/yocto/2013-November/017042.html
SRCREV = "4a31eb5460d549032e18f749187c2581ebb3dd91"
PVBASE := "${PV}"
PV = "${PVBASE}.${SRCPV}"

inherit autotools

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
	./bootstrap --force
	cd ${currentdir}
}
