<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- ======================================= -->
<!-- File    : REM_Stakeholder.xsl           -->
<!-- Author  : Amador Duran Toro             -->
<!-- Date    : 2001/09/22                    -->
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
<!-- rem:stakeholder template                      -->
<!-- ============================================= -->

<xsl:template match="rem:stakeholder">
    <xsl:call-template name="GenerateAnchor"/>

    <table border="1" width="100%">    
        <xsl:apply-templates select="rem:name"/>

        <tr><th align="left" valign="top"><xsl:value-of select="$rem:lang_organization"/></th>
            <td align="left" valign="top">
            <xsl:choose>
                <xsl:when test="not(rem:worksFor)"><xsl:value-of select="$rem:lang_freelance"/></xsl:when>
                <xsl:otherwise>
                    <a><xsl:attribute name="href">#<xsl:value-of select="rem:worksFor/@organization"/></xsl:attribute>
                    <xsl:value-of select="id(rem:worksFor/@organization)/rem:name"/></a>        
                </xsl:otherwise>
            </xsl:choose>
            </td>
        </tr>

        <xsl:call-template name="GenerateRow">
           <xsl:with-param name="title"><xsl:value-of select="$rem:lang_role"/></xsl:with-param>
           <xsl:with-param name="node" select="rem:stakeholderRole"/>            
        </xsl:call-template>

        <tr><th align="left" valign="top" nowrap="1">
				<xsl:value-of select="$rem:lang_isDeveloper"/>
			</th>
            <td align="left" valign="top">
            <xsl:choose>
                <xsl:when test="not(rem:isDeveloper)">
					<xsl:value-of select="$rem:lang_no"/>
				</xsl:when>
                <xsl:otherwise>
					<xsl:value-of select="$rem:lang_yes"/>
				</xsl:otherwise>
            </xsl:choose>
            </td>
        </tr>

        <tr><th align="left" valign="top">
				<xsl:value-of select="$rem:lang_isCustomer"/>
			</th>
            <td align="left" valign="top">
            <xsl:choose>
                <xsl:when test="not(rem:isCustomer)">
					<xsl:value-of select="$rem:lang_no"/>
				</xsl:when>
                <xsl:otherwise>
					<xsl:value-of select="$rem:lang_yes"/>				
				</xsl:otherwise>
            </xsl:choose>
            </td>
        </tr>

        <tr><th align="left" valign="top">
			<xsl:value-of select="$rem:lang_isUser"/>		
		</th>
            <td align="left" valign="top">
            <xsl:choose>
                    <xsl:when test="not(rem:isUser)">
					<xsl:value-of select="$rem:lang_no"/>
				</xsl:when>
                <xsl:otherwise>
					<xsl:value-of select="$rem:lang_yes"/>				
				</xsl:otherwise>
            </xsl:choose>
            </td>
        </tr>
    
        <xsl:call-template name="SpecificationObject_Bottom"/>    
    </table>
    <p/>
</xsl:template>

<!-- ============================================= -->
<!-- rem:stakeholder/rem:name template             -->
<!-- ============================================= -->

<!-- NOTE: anchors inside tables don't work in IE 5.5 -->

<xsl:template match="rem:stakeholder/rem:name">
    <tr>
		<xsl:attribute name="bgcolor">
			<xsl:value-of select="$rem:header_background"/>
		</xsl:attribute>
        <th align="left" valign="top"><xsl:value-of select="$rem:lang_stakeholder"/></th>
        <th align="left" valign="top" width="100%">
        <xsl:apply-templates/>
    </th>
    </tr>
</xsl:template>

</xsl:stylesheet>
