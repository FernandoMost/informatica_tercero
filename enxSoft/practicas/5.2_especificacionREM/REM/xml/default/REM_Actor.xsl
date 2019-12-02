<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_Actor.xsl                     -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.0                               -->
<!-- =========================================== -->

<!-- Stylesheet for Actors                     -->

<!-- Last update : 2002/05/29                  -->
<!-- Namespace URI has changed                 -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:actor template                            -->
<!-- ============================================= -->

<xsl:template match="rem:actor">
    <xsl:call-template name="GenerateAnchor"/>

    <table border="1" width="100%">    
        <xsl:call-template name="SpecificationObject_Top"/>    

        <tr>
			<th align="left" valign="top">
				<xsl:value-of select="$rem:lang_description"/>
			</th>

            <td align="left" valign="top">
				<xsl:value-of select="$rem:lang_this_actor_represents"/>			
		        <em>
					<xsl:apply-templates select="rem:actorRole"/>
				</em>
			</td> 
        </tr>

        <xsl:call-template name="SpecificationObject_Bottom"/>
    </table>

    <p/>
</xsl:template>

</xsl:stylesheet>
