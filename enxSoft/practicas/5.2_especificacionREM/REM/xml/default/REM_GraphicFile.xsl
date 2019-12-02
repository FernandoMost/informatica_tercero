<?xml version="1.0" encoding="iso-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_GraphicFile.xsl               -->
<!-- Content : Stylesheet for Graphic Files      -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2002/05/04                        -->
<!-- Version : 1.1                               -->
<!-- =========================================== -->
<!-- Last update : 2002/05/29                    -->
<!-- Now, we use the disable-output-escaping     -->
<!-- when generating the graphic file name.      -->
<!-- Namespace URI has changed                   -->
<!-- =========================================== -->
<!-- Last update : 2004/09/19                    -->
<!-- <a> tags now use title attribute            -->
<!-- =========================================== -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:graphicFile template                      -->
<!-- ============================================= -->

<xsl:template match="rem:graphicFile">
    <xsl:call-template name="GenerateAnchor"/>

    <xsl:choose>

        <xsl:when test="contains(rem:fileName,'.doc')">
            <a>
            <xsl:attribute name="href">
                <xsl:value-of select="rem:fileName" disable-output-escaping="yes" />
            </xsl:attribute>
            <!-- This string should be internationalized -->
            <xsl:attribute name="title">Click here to open the file</xsl:attribute>
            <xsl:value-of select="rem:fileName" disable-output-escaping="yes" />
            </a>
        </xsl:when>

        <xsl:otherwise>
            <p align="center">
                <img>
                    <!-- Change the 100% or delete it if you don't like the image to be automatically resized -->
                    <!-- <xsl:attribute name="width">100%</xsl:attribute> -->
                    <xsl:attribute name="src">
                        <xsl:value-of select="rem:fileName" disable-output-escaping="yes" />
                    </xsl:attribute>
                    <xsl:attribute name="title">
                        <xsl:value-of select="rem:fileName" disable-output-escaping="yes" />
                    </xsl:attribute>
                </img>
            </p>

            <p align="center">
                <b><xsl:value-of select="$rem:lang_figure"/><xsl:text> </xsl:text><xsl:number/></b>:
                <xsl:value-of select="rem:name"/>
            </p>
        </xsl:otherwise>

    </xsl:choose>

</xsl:template>

</xsl:stylesheet>
