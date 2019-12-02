<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_AffectedObjects.xsl           -->
<!-- Content : Stylesheet for AffectedObjects    -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.0                               -->
<!-- =========================================== -->
<!-- Last update : 2002/05/29                    -->
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
<!-- DirectlyAffectedObjects template              -->
<!-- ============================================= -->

<xsl:template name="DirectlyAffectedObjects">
    <tr>
        <th align="left" valign="top">
            <xsl:value-of select="$rem:lang_directly_affected_objects"/>
        </th>

        <td valign="top">
            <xsl:choose>
                <xsl:when test="not(rem:directlyAffects)">
                    <xsl:value-of select="$rem:lang_none"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:for-each select="id(rem:directlyAffects/@affected)">
                    <xsl:sort select="@oid"/>
                        <li>
                            <a>
                                <xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute>
                                <xsl:attribute name="title"><xsl:value-of select="normalize-space(rem:name)"/></xsl:attribute>
                                [<xsl:value-of select="@oid"/>] <xsl:value-of select="rem:name"/>
                            </a>
                            <br/>
                        </li>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
        </td>
    </tr>
</xsl:template>

<!-- ============================================= -->
<!-- IndirectlyAffectedObjects template            -->
<!-- ============================================= -->

<xsl:template name="IndirectlyAffectedObjects">
    <tr>
        <th align="left" valign="top">
            <xsl:value-of select="$rem:lang_indirectly_affected_objects"/>
        </th>

        <td valign="top">
            <xsl:choose>
                <xsl:when test="not(rem:indirectlyAffects)">
                    <xsl:value-of select="$rem:lang_none"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:for-each select="id(rem:indirectlyAffects/@affected)">
                    <xsl:sort select="@oid"/>
                        <li>
                            <a>
                                <xsl:attribute name="href">#<xsl:value-of select="@oid"/></xsl:attribute>
                                <xsl:attribute name="title"><xsl:value-of select="normalize-space(rem:name)"/></xsl:attribute>
                                [<xsl:value-of select="@oid"/>] <xsl:value-of select="rem:name"/>
                            </a>
                            <br/>
                        </li>
                    </xsl:for-each>
                </xsl:otherwise>
            </xsl:choose>
        </td>
    </tr>
</xsl:template>

</xsl:stylesheet>
