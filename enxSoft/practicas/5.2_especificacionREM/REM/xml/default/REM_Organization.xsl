<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- ======================================= -->
<!-- File    : REM_Organization.xsl          -->
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

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:organization template                     -->
<!-- ============================================= -->

<xsl:template match="rem:organization">
    <xsl:call-template name="GenerateAnchor"/>

    <table border="1" width="100%">
        <xsl:apply-templates select="rem:name"/>

        <xsl:call-template name="GenerateRow">
            <xsl:with-param name="title">
                <xsl:value-of select="$rem:lang_address"/>
            </xsl:with-param>
            <xsl:with-param name="node" select="rem:address"/>
        </xsl:call-template>

        <xsl:call-template name="GenerateRow">
            <xsl:with-param name="title">
                <xsl:value-of select="$rem:lang_telephone"/>
            </xsl:with-param>
            <xsl:with-param name="node" select="rem:telephone"/>            
        </xsl:call-template>

        <xsl:call-template name="GenerateRow">
            <xsl:with-param name="title">
                <xsl:value-of select="$rem:lang_fax"/>
            </xsl:with-param>
            <xsl:with-param name="node" select="rem:fax"/>            
        </xsl:call-template>

        <xsl:call-template name="SpecificationObject_Bottom"/>
    </table>
    <p/>
</xsl:template>

<!-- ============================================= -->
<!-- rem:organization/rem:name template            -->
<!-- ============================================= -->

<!-- NOTE: anchors inside tables don't work in IE 5.5 -->

<xsl:template match="rem:organization/rem:name">
    <tr>
		<xsl:attribute name="bgcolor">
			<xsl:value-of select="$rem:header_background"/>
		</xsl:attribute>
        <th align="left" valign="top"><xsl:value-of select="$rem:lang_organization"/></th>
        <th align="left" valign="top" width="100%">
        <xsl:apply-templates/>
    </th>
    </tr>
</xsl:template>

</xsl:stylesheet>
