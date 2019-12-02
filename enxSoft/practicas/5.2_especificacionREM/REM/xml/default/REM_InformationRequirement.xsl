<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_InformationRequirement.xsl    -->
<!-- Content : Stylesheet for Information Reqs.  -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.2                               -->
<!-- =========================================== -->
<!-- Last update : 2002/07/14                    -->
<!-- header_background variable used and         -->
<!-- <ul> used for specific data list and        -->
<!-- comments of specific data are shown         -->
<!-- =========================================== -->
<!-- Last update : 2002/05/29                    -->
<!-- Namespace URI has changed                   -->
<!-- =========================================== -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================== -->
<!-- rem:informationRequirement template            -->
<!-- ============================================== -->

<!-- Note the use of colspan=2 -->

<xsl:template match="rem:informationRequirement">
    <xsl:call-template name="GenerateAnchor"/>
    
    <table border="1" width="100%">    

        <xsl:call-template name="C_Requirement_Top">
            <xsl:with-param name="span">2</xsl:with-param>
        </xsl:call-template>

        <!-- rem:description -->

        <tr>
			<th align="left" valign="top">
				<xsl:value-of select="$rem:lang_description"/>
			</th>
            
			<td colspan="2">
				<xsl:value-of select="$rem:lang_irq_lp_01"/> 
				<em>
					<xsl:apply-templates select="rem:relevantConcept"/>.
				</em>
				<xsl:value-of select="$rem:lang_irq_lp_02"/> 
			</td>
        </tr>

        <!-- Specific Data -->

        <tr>
			<th align="left" valign="top">
				<xsl:value-of select="$rem:lang_specific_data"/> 
			</th>

            <td colspan="2" align="left" valign="top">
				<xsl:choose>
					<xsl:when test="not(rem:specificData)">
						<xsl:value-of select="$rem:lang_none"/> 
					</xsl:when>
                <xsl:otherwise>
					<ul>
                    <xsl:for-each select="rem:specificData">
                        <li>
                            <xsl:call-template name="GenerateAnchor"/>
                            <xsl:apply-templates select="rem:description"/>
							<xsl:if test="(normalize-space(rem:comments) != $rem:lang_none) and string-length(normalize-space(rem:comments)) > 0">
								(<em><xsl:apply-templates select="rem:comments"/></em>)
							</xsl:if>
                        </li>
                    </xsl:for-each>
					</ul>
                </xsl:otherwise>
            </xsl:choose>
            </td>
        </tr>
        
        <!-- Lifetime -->

        <tr>
			<th rowspan="2" align="left" valign="top">
				<xsl:value-of select="$rem:lang_lifetime"/> 
			</th>
            <th align="left" valign="top">
				<xsl:value-of select="$rem:lang_average"/> 
			</th>
            <th align="left" valign="top">
				<xsl:value-of select="$rem:lang_maximum"/> 
			</th>
        </tr>
            
        <tr>
            <td>
                <xsl:choose>
                    <xsl:when test="rem:averageLifeTime/rem:tbd">
                        <xsl:apply-templates select="rem:averageLifeTime/rem:tbd"/> 
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="rem:averageLifeTime/rem:value"/>&#32;
                        <xsl:value-of select="id(rem:averageLifeTime/rem:timeUnit/@value)/rem:name"/>
                    </xsl:otherwise>
                </xsl:choose>
            </td>

            <td>
                <xsl:choose>
                    <xsl:when test="rem:maximumLifeTime/rem:tbd">
                        <xsl:apply-templates select="rem:maximumLifeTime/rem:tbd"/> 
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="rem:maximumLifeTime/rem:value"/>&#32;
                        <xsl:value-of select="id(rem:maximumLifeTime/rem:timeUnit/@value)/rem:name"/>                   
                    </xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
        
        <!-- Ocurrences -->

        <tr>
			<th rowspan="2" align="left" valign="top">
				<xsl:value-of select="$rem:lang_simultaneous_ocurrences"/> 				
			</th>
            <th align="left" valign="top">
				<xsl:value-of select="$rem:lang_average"/> 
			</th>
            <th align="left" valign="top">
				<xsl:value-of select="$rem:lang_maximum"/> 
			</th>
        </tr>
            
        <tr>
            <td>
                <xsl:choose>
                    <xsl:when test="rem:averageOcurrences/rem:tbd">
                        <xsl:apply-templates select="rem:averageOcurrences/rem:tbd"/> 
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="rem:averageOcurrences/rem:value"/>
                    </xsl:otherwise>
                </xsl:choose>
            </td>

            <td>
                <xsl:choose>
                    <xsl:when test="rem:maximumOcurrences/rem:tbd">
                        <xsl:apply-templates select="rem:maximumOcurrences/rem:tbd"/> 
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="rem:maximumOcurrences/rem:value"/>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>

        <xsl:call-template name="C_Requirement_Bottom">
            <xsl:with-param name="span">2</xsl:with-param>                        
        </xsl:call-template>

    </table>
    <p/>
</xsl:template>

<!-- ============================================== -->
<!-- rem:informationRequirement/rem:name template   -->
<!-- ============================================== -->

<!-- Note the use of colspan=2 -->

<xsl:template match="rem:informationRequirement/rem:name">
    <tr>
		<xsl:attribute name="bgcolor">
			<xsl:value-of select="$rem:header_background"/>
		</xsl:attribute>
        <th align="left" valign="top">
			<xsl:value-of select="../@oid"/>
		</th>
        <th align="left" valign="top" width="100%" colspan="2">
			<xsl:apply-templates/>
		</th>
    </tr>
</xsl:template>

<!-- ================================================ -->
<!-- rem:informationRequirement/rem:version template  -->
<!-- ================================================ -->

<xsl:template match="rem:informationRequirement/rem:version">
    <tr>
		<th align="left" valign="top">
			<xsl:value-of select="$rem:lang_version"/>
		</th>
        <td align="left" valign="top" colspan="2">
            <xsl:value-of select="rem:major"/>.<xsl:value-of select="rem:minor"/> 
			( <xsl:apply-templates select="rem:date"/> )
        </td>
    </tr>
</xsl:template>

</xsl:stylesheet>
