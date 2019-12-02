<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_FunctionalRequirement.xsl    -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.0                               -->
<!-- =========================================== -->

<!-- Stylesheet for FunctionalRequirements    -->
<!-- Last update : 2002/05/29                  -->
<!-- Namespace URI has changed                 -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================== -->
<!-- rem:functionalRequirement template             -->
<!-- ============================================== -->

<xsl:template match="rem:functionalRequirement">
    <xsl:call-template name="GenerateAnchor"/>

    <table border="1" width="100%">    
        <xsl:call-template name="C_Requirement_Top"/>

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

        <xsl:call-template name="C_Requirement_Bottom"/>
    </table>
    <p/>
</xsl:template>

</xsl:stylesheet>
