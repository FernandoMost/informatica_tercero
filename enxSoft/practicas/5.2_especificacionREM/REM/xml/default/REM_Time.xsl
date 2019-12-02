<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_Time.xsl                      -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.0                               -->
<!-- =========================================== -->

<!-- Stylesheet for Times                     -->
<!-- Last update : 2002/05/29                  -->
<!-- Namespace URI has changed                 -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:time template                             -->
<!-- ============================================= -->

<xsl:template match="rem:time">
	<xsl:value-of select="format-number(rem:hour,'00')"/>:<xsl:value-of select="format-number(rem:minute,'00')"/>
</xsl:template>

</xsl:stylesheet>
