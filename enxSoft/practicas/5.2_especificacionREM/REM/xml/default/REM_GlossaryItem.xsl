<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_GlossaryItem.xsl              -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.0                               -->
<!-- =========================================== -->

<!-- Stylesheet for Glossary Items               -->
<!-- Last update : 2002/05/29                  -->
<!-- Namespace URI has changed                 -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:glossaryItem template                     -->
<!-- ============================================= -->

<xsl:template match="rem:glossaryItem">
    <xsl:call-template name="GenerateAnchor"/>
    <p align="left">
		<b><xsl:value-of select="rem:name"/>:</b>
		<xsl:apply-templates select="rem:text"/>
    </p>
</xsl:template>

</xsl:stylesheet>
