<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- ============================================ -->
<!-- File    : REM_OperationException.xsl         -->
<!-- Content : Stylesheet for OperationExceptions -->
<!-- Author  : Amador Duran Toro                  -->
<!-- Date    : 2001/01/30                         -->
<!-- Version : 1.0                                -->
<!-- ============================================ -->
<!-- Last update : 2002/05/29                     -->
<!-- Namespace URI has changed                    -->
<!-- ============================================ -->
<!-- Last update : 2004/09/15                     -->
<!-- Bgcolor attribute fixed in name template     -->
<!-- ============================================ -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================== -->
<!-- rem:operationException template                -->
<!-- ============================================== -->

<xsl:template match="rem:operationException">
    <xsl:call-template name="GenerateAnchor"/>

    <table border="1" width="100%">

	    <xsl:apply-templates select="rem:name"/>    

		<xsl:call-template name="GenerateRow"> 
			<xsl:with-param name="title">
				<xsl:value-of select="$rem:lang_condition"/>		
			</xsl:with-param> 
			<xsl:with-param name="node" select="rem:operationCondition/rem:expression/rem:natural"/> 
		</xsl:call-template> 

		<tr>    
			<th align="left" valign="top">
				<xsl:value-of select="$rem:lang_OCL_condition"/>
			</th>
			<td align="left" valign="top">
	            <code><xsl:value-of select="rem:operationCondition/rem:expression/rem:ocl"/></code>
		    </td>
		</tr>

	    <xsl:call-template name="GenerateRow"> 
			<xsl:with-param name="title">
				<xsl:value-of select="$rem:lang_expression"/>		
			</xsl:with-param> 
			<xsl:with-param name="node" select="rem:expression/rem:natural"/> 
		</xsl:call-template> 

	    <tr>    
		    <th align="left" valign="top">
				<xsl:value-of select="$rem:lang_OCL_expression"/>		
			</th>
			<td align="left" valign="top">
				<code><xsl:value-of select="rem:expression/rem:ocl"/></code>
			</td>
		</tr>

	    <xsl:call-template name="GenerateRow"> 
		    <xsl:with-param name="title">
				<xsl:value-of select="$rem:lang_comments"/>		
			</xsl:with-param>
			<xsl:with-param name="node" select="rem:comments"/> 
	    </xsl:call-template>

    </table>

    <p/>
</xsl:template>

<!-- ============================================== -->
<!-- rem:operationException/rem:name template       -->
<!-- ============================================== -->

<xsl:template match="rem:operationException/rem:name">
    <tr>
		<xsl:attribute name="bgcolor">
			<xsl:value-of select="$rem:header_background"/>
		</xsl:attribute>
        <th align="left" valign="top" nowrap="1">
			<xsl:value-of select="$rem:lang_exception"/>		
		</th>
        <th align="left" valign="top" width="100%">
            <xsl:value-of select="../../rem:name"/>::<xsl:value-of select="."/>
        </th>
    </tr>
</xsl:template>

</xsl:stylesheet>
