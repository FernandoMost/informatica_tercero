<?xml version="1.0" encoding="ISO-8859-1"?>

<!-- =========================================== -->
<!-- File    : REM_Section.xsl                   -->
<!-- Author  : Amador Duran Toro                 -->
<!-- Date    : 2001/01/30                        -->
<!-- Version : 1.0                               -->
<!-- =========================================== -->

<!-- Stylesheet for Sections -->
<!-- Last update : 2002/05/29                  -->
<!-- Namespace URI has changed                 -->

<xsl:stylesheet
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rem="http://rem.lsi.us.es"
    version="1.0">

<!-- ============================================= -->
<!-- rem:section template                          -->
<!-- ============================================= -->

<xsl:template match="rem:section">
    <xsl:call-template name="GenerateAnchor"/>
    <xsl:if test="rem:level='1'"><h1 align="left"><xsl:value-of select="rem:number"/> <xsl:value-of select="rem:name"/></h1></xsl:if>
    <xsl:if test="rem:level='2'"><h2 align="left"><xsl:value-of select="rem:number"/> <xsl:value-of select="rem:name"/></h2></xsl:if>
    <xsl:if test="rem:level='3'"><h3 align="left"><xsl:value-of select="rem:number"/> <xsl:value-of select="rem:name"/></h3></xsl:if>
    <xsl:if test="rem:level='4'"><h4 align="left"><xsl:value-of select="rem:number"/> <xsl:value-of select="rem:name"/></h4></xsl:if>
    <xsl:if test="rem:level='5'"><h5 align="left"><xsl:value-of select="rem:number"/> <xsl:value-of select="rem:name"/></h5></xsl:if>
    <xsl:if test="rem:level='6'"><h6 align="left"><xsl:value-of select="rem:number"/> <xsl:value-of select="rem:name"/></h6></xsl:if>                    
    <xsl:apply-templates select=
        "rem:section                  | 
         rem:paragraph                | 
         rem:graphicFile              |
         rem:glossaryItem             | 
         rem:organization             | 
         rem:stakeholder              | 
         rem:meeting                  | 
         rem:objective                | 
         rem:actor                    |
         rem:informationRequirement   |
         rem:constraintRequirement    |
         rem:useCase                  |
         rem:functionalRequirement    |
         rem:nonFunctionalRequirement |
         rem:objectType               |
         rem:userDefinedValueType     |
         rem:associationType          |
         rem:systemOperation          |
		 rem:conflict                 |
		 rem:defect                   |
		 rem:changeRequest			  |
         rem:traceabilityMatrix"
    />
</xsl:template>

<!-- ============================================== -->
<!-- rem:section/rem:authors|rem:sources template   -->
<!-- ============================================== -->

<xsl:template match="(rem:section|rem:appendix)/rem:authors">
</xsl:template>

<xsl:template match="(rem:section|rem:appendix)/rem:sources">
</xsl:template>

</xsl:stylesheet>
