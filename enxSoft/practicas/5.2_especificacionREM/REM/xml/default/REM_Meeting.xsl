<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- ======================================= -->
<!-- File    : REM_Meeting.xsl               -->
<!-- Content : Stylesheet for REM Meetings   -->
<!-- Author  : Amador Duran Toro             -->
<!-- Date    : 2001/01/30                    -->
<!-- Version : 1.2                           -->
<!-- ======================================= -->
<!-- Last update : 2002/07/18                -->
<!-- header_background variable used         -->
<!-- ======================================= -->
<!-- Last update : 2002/05/29                -->
<!-- Namespace URI has changed               -->
<!-- ======================================= -->
<!-- Last update : 2004/09/19                -->
<!-- <a> tags now use title attribute        -->
<!-- ======================================= -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:meeting template                          -->
<!-- ============================================= -->

<xsl:template match="rem:meeting">
    <xsl:call-template name="GenerateAnchor"/>

    <table border="1" width="100%">

        <xsl:apply-templates select="rem:name"/>

        <xsl:call-template name="GenerateRow">
            <xsl:with-param name="title">
                <xsl:value-of select="$rem:lang_date"/>
            </xsl:with-param>
            <xsl:with-param name="node" select="rem:date"/>
        </xsl:call-template>

        <xsl:call-template name="GenerateRow">
            <xsl:with-param name="title">
                <xsl:value-of select="$rem:lang_time"/>
            </xsl:with-param>
            <xsl:with-param name="node" select="rem:time"/>
        </xsl:call-template>

        <xsl:call-template name="GenerateRow">
            <xsl:with-param name="title">
                <xsl:value-of select="$rem:lang_place"/>
            </xsl:with-param>
            <xsl:with-param name="node" select="rem:place"/>
        </xsl:call-template>

        <tr><th align="left" valign="top">
                <xsl:value-of select="$rem:lang_attenders"/>
            </th>
            <td align="left" valign="top">
            <xsl:choose>
                <xsl:when test="not(rem:attenders)">
                    <font color="red"><b>?</b></font>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:apply-templates select="rem:attenders"/>
                </xsl:otherwise>
            </xsl:choose>
            </td>
        </tr>

        <xsl:call-template name="GenerateRow">
            <xsl:with-param name="title">
                <xsl:value-of select="$rem:lang_results"/>
            </xsl:with-param>
            <xsl:with-param name="node" select="rem:results"/>
        </xsl:call-template>

        <xsl:call-template name="SpecificationObject_Bottom"/>
    </table>
    <p/>
</xsl:template>

<!-- ============================================== -->
<!-- rem:meeting/rem:name template                  -->
<!-- ============================================== -->

<xsl:template match="rem:meeting/rem:name">
    <tr>
        <xsl:attribute name="bgcolor">
            <xsl:value-of select="$rem:header_background"/>
        </xsl:attribute>
        <th align="left" valign="top">
            <xsl:value-of select="$rem:lang_meeting"/>
        </th>
        <th align="left" valign="top" width="100%">
            <xsl:apply-templates/>
        </th>
    </tr>
</xsl:template>

<!-- ============================================== -->
<!-- rem:attenders template                         -->
<!-- ============================================== -->

<xsl:template match="rem:attenders">
    <ul>
        <xsl:for-each select="id(@stakeholders)">
        <xsl:sort select="rem:name"/>
            <li>
                <a>
                    <xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute>
                    <xsl:attribute name="title"><xsl:value-of select="normalize-space(rem:name)"/></xsl:attribute>
                    <xsl:value-of select="rem:name"/>
                </a>
            </li>
        </xsl:for-each>
    </ul>
</xsl:template>

</xsl:stylesheet>
