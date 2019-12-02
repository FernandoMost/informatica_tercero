<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_Objective.xsl                 -->
<!-- Content : Stylesheet for REM Objectives     -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2002/07/12                        -->
<!-- Version : 1.2                               -->
<!-- =========================================== -->
<!-- Last update : 2002/07/14                    -->
<!-- Comments of subojectives are shown          -->
<!-- =========================================== -->
<!-- Last update : 2002/07/12                    -->
<!-- First version                               -->
<!-- =========================================== -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================== -->
<!-- rem:objective template (top)   -->
<!-- ============================== -->

<xsl:template match="rem:objective[not(parent::rem:objective)]">
    <xsl:call-template name="GenerateAnchor"/>

    <table border="1" width="100%">
        <xsl:call-template name="SpecificationObject_Top"/>

        <tr>
            <th align="left" valign="top">
                <xsl:value-of select="$rem:lang_description"/>
            </th>
            <td align="left" valign="top">
                <xsl:value-of select="$rem:lang_the_system_shall"/>
                <em>
                    <xsl:apply-templates select="rem:description"/>
                </em>
            </td>
        </tr>

        <!-- subobjectives -->

        <tr>
            <th align="left" valign="top">
                <xsl:value-of select="$rem:lang_subobjectives"/>
            </th>

            <td align="left" valign="top">
                <xsl:choose>
                    <xsl:when test="not(rem:objective)">
                        <xsl:value-of select="$rem:lang_none"/>
                    </xsl:when>
                    <xsl:otherwise>
						<ul>
							<xsl:apply-templates select="rem:objective"/>
						</ul>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>

        <xsl:call-template name="C_Requirement_Bottom"/>

    </table>
    <p/>
</xsl:template>

<!-- ============================== -->
<!-- rem:objective template (child) -->
<!-- ============================== -->

<xsl:template match="rem:objective[parent::rem:objective]">
	<xsl:call-template name="GenerateAnchor"/>
	<li>
		<b>[<xsl:value-of select="@oid"/>] <xsl:value-of select="rem:name"/></b>:
		<xsl:value-of select="$rem:lang_the_system_shall"/>
		<em><xsl:apply-templates select="rem:description"/></em>
		<xsl:text> </xsl:text>
		<xsl:if test="(normalize-space(rem:comments) != $rem:lang_none) and string-length(normalize-space(rem:comments)) > 0">
			(<em><xsl:apply-templates select="rem:comments"/></em>)
		</xsl:if>
       <ul>	
           <xsl:apply-templates select="rem:objective"/>
       </ul>
    </li>
</xsl:template>

</xsl:stylesheet>
