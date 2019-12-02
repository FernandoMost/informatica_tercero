<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_Paragraph.xsl                 -->
<!-- Content : Stylesheet for REM paragraphs     -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.0                               -->
<!-- =========================================== -->
<!-- Last update : 2002/05/29                    -->
<!-- Namespace URI has changed                   -->
<!-- =========================================== -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:paragraph template                        -->
<!-- ============================================= -->

<xsl:template match="rem:paragraph">
    <xsl:call-template name="GenerateAnchor"/>
    <p>
        <xsl:apply-templates select="rem:text"/>
    </p>
</xsl:template>

</xsl:stylesheet>
