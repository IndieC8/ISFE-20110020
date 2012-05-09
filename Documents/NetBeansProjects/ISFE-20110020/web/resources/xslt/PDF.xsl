<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:cfdi="http://www.sat.gob.mx/cfd/3" xmlns:tfd="http://www.sat.gob.mx/TimbreFiscalDigital" xsi:schemaLocation="http://www.sat.gob.mx/cfd/3 cfdv3.xsd" xmlns:qr="http://code.google.com/p/fop-qrcode">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="Factura" page-height="11in" page-width="8.5in" margin="1cm">
					<fo:region-body margin="0in" margin-top="0.5in" margin-bottom="0.25in"/>
					<fo:region-before extent="0in"/>
					<fo:region-after extent="0in"/>
					<fo:region-start extent="0in"/>
					<fo:region-end extent="0in"/>
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:declarations>
				<x:xmpmeta xmlns:x="adobe:ns:meta/">
					<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
						<rdf:Description rdf:about="" xmlns:dc="http://purl.org/dc/elements/1.1/">
						<!-- Dublin Core properties go here -->
							<dc:title>Factura emitida con ISFE</dc:title>
							<dc:creator>ISFE</dc:creator>
							<dc:description>CFDI</dc:description>
						</rdf:Description>
						<rdf:Description rdf:about="" xmlns:xmp="http://ns.adobe.com/xap/1.0/">
						<!-- XMP properties go here -->
							<xmp:CreatorTool>SFE</xmp:CreatorTool>
						</rdf:Description>
						<rdf:Description rdf:about="" xmlns:pdf="http://ns.adobe.com/pdf/1.3/">
							<!-- XMP properties go here -->
							<pdf:Producer>ISFE - Apache FOP</pdf:Producer>
						</rdf:Description>
					</rdf:RDF>
				</x:xmpmeta>
			</fo:declarations>

			<fo:page-sequence master-reference="Factura" font-size="11pt">
				<fo:static-content flow-name="xsl-region-after" font-size="11pt">
					<fo:block text-align-last="justify">
						Este documento es una representación impresa de un CFDI
						<fo:leader leader-pattern="space"/>
						<fo:page-number/> de
						<fo:page-number-citation ref-id="last-page"/>
					</fo:block>
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-before" font-size="14pt" font-weight="bold">
					<fo:block text-align="center">Factura emitida con ISFE.</fo:block>
				</fo:static-content>
                                <fo:flow flow-name="xsl-region-body">
                                    <fo:block>
                                        <xsl:apply-templates/>
                                    </fo:block>
                                </fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="cfdi:Emisor">
		<fo:block space-after="0.5cm">
			<fo:table table-layout="fixed">
				<fo:table-column column-width="proportional-column-width(1.5)"/>
				<fo:table-column column-width="proportional-column-width(1)"/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block font-weight="bold" font-size="12pt" color="#ffffff" border="solid 1pt #212A3B" background-color="#212A3B">Emisor:</fo:block>
							<fo:block id="Emisor" border="solid 1pt #212A3B">
								<fo:block font-size="12pt" wrap-option="wrap" font-weight="bold">
									<xsl:value-of select="@nombre"/>
								</fo:block>
								<fo:block font-size="12pt" wrap-option="wrap" font-weight="bold">
									RFC:
									<xsl:value-of select="@rfc"/>
								</fo:block>
								<fo:block font-size="11pt" wrap-option="wrap" font-weight="normal">
									<xsl:value-of select="./cfdi:DomicilioFiscal/@calle"/>&#0160;
									<xsl:value-of select="./cfdi:DomicilioFiscal/@noExterior"/>&#0160;
									Col.
									<xsl:value-of select="./cfdi:DomicilioFiscal/@colonia"/>&#0160;
								</fo:block>
								<fo:block>
									<xsl:if test="./cfdi:DomicilioFiscal/@estado = 'Distrito Federal'">Del. </xsl:if>
									<xsl:if test="./cfdi:DomicilioFiscal/@estado != 'Distrito Federal'">Mpo. </xsl:if>
									<xsl:value-of select="./cfdi:DomicilioFiscal/@municipio"/>
								</fo:block>
								<fo:block>
									C.P.
									<xsl:value-of select="./cfdi:DomicilioFiscal/@codigoPostal"/>&#0160;
									<xsl:value-of select="./cfdi:DomicilioFiscal/@estado"/>&#0160;
									<xsl:value-of select="./cfdi:DomicilioFiscal/@pais"/>.&#0160;
								</fo:block>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block id="TimbreFiscalDigital" text-align="right">
								<fo:block font-weight="bold">Folio Fiscal:</fo:block>
								<fo:block>
									<xsl:value-of select="../cfdi:Complemento/tfd:TimbreFiscalDigital/@UUID"/>
								</fo:block>
								<fo:block font-weight="bold">No. de Serie del Certificado del SAT:</fo:block>
								<fo:block>
									<xsl:value-of select="../@noCertificado"/>
								</fo:block>
								<fo:block font-weight="bold">Fecha y hora certificación:</fo:block>
								<fo:block>
									<xsl:value-of select="../@fecha"/>
								</fo:block>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template match="cfdi:Receptor">
		<fo:block space-after="0.5cm">
			<fo:table>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block id="Receptor">
								<fo:block font-weight="bold">Receptor: </fo:block>
								<fo:block>
									<xsl:value-of select="@nombre"/>
								</fo:block>
								<fo:block>RFC:
									<xsl:value-of select="@rfc"/>
								</fo:block>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block id="Expedicion" text-align="end">
								<fo:block>
									<fo:block font-weight="bold">Lugar y fecha de expedición:</fo:block>
									<fo:block>
										<xsl:value-of select="../cfdi:Emisor/cfdi:ExpedidoEn/@estado"/>&#0160;
										<xsl:value-of select="../@fecha"/>
									</fo:block>
								</fo:block>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
	</xsl:template>

	<xsl:template match="cfdi:Conceptos">
		<fo:block space-after="0.5cm">
			<fo:block id="Conceptos">
				<fo:table wrap-option="wrap" border="solid 1pt #C0C0C0" display-align="center" table-layout="fixed">
					<fo:table-column column-width="proportional-column-width(1)" />
					<fo:table-column column-width="proportional-column-width(1.5)" />
					<fo:table-column column-width="proportional-column-width(4)" />
					<fo:table-column column-width="proportional-column-width(1.5)" />
					<fo:table-column column-width="proportional-column-width(1.5)" />
					<fo:table-header table-omit-header-at-break="false">
						<fo:table-row font-weight="bold" background-color="#212A3B" color="#ffffff" text-align="center">
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block>Cantidad</fo:block>
							</fo:table-cell>
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block>U. de Medida</fo:block>
							</fo:table-cell>
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block>Descripción</fo:block>
							</fo:table-cell>
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block>Precio Unitario</fo:block>
							</fo:table-cell>
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block>Importe</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-header>
					<fo:table-body text-align="center">
						<xsl:for-each select="cfdi:Concepto">
							<fo:table-row >
								<fo:table-cell border="solid 1pt #c0c0c0">
									<fo:block>
										<xsl:value-of select="@cantidad"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell border="solid 1pt #c0c0c0">
									<fo:block>
										<xsl:value-of select="@unidad"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell border="solid 1pt #c0c0c0">
									<fo:block font-weight="bold">
										<xsl:value-of select="@descripcion"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell border="solid 1pt #c0c0c0">
									<fo:block>
										$
										<xsl:value-of select="@valorUnitario"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell border="solid 1pt #c0c0c0">
									<fo:block>
										$
										<xsl:value-of select="@importe"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:for-each>

						<fo:table-row font-weight="bold">
							<fo:table-cell column-number="4" border="solid 1pt #c0c0c0">
								<fo:block  text-align="right">Subtotal:&#0160;</fo:block>
							</fo:table-cell>
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block >&#0160;$
									<xsl:value-of select="../../cfdi:Comprobante/@subTotal"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row font-weight="bold">
							<fo:table-cell column-number="4" border="solid 1pt #c0c0c0">
								<fo:block  text-align="right"> I.V.A.:&#0160;</fo:block>
							</fo:table-cell>
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block >&#0160;$
									<xsl:value-of select="../cfdi:Impuestos/cfdi:Traslados/cfdi:Traslado[1]/@importe"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
						<fo:table-row font-weight="bold" color="#212A3B">
							<fo:table-cell column-number="4" border="solid 1pt #c0c0c0">
								<fo:block  text-align="right">Total:&#0160;</fo:block>
							</fo:table-cell>
							<fo:table-cell border="solid 1pt #c0c0c0">
								<fo:block >&#0160;$
									<xsl:value-of select="../../cfdi:Comprobante/@total"/>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</fo:block>
		</fo:block>
	</xsl:template>

	<xsl:template match="cfdi:Complemento">

		<fo:block space-after="0.5cm" wrap-option="wrap">
			<fo:table>
				<fo:table-column column-width="24.5%"/>
				<fo:table-column />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block>
								<!--<fo:external-graphic src="url('http://localhost:8084/ISFE/resources/QR.gif')" content-height="40" content-width="40"/>-->
							</fo:block>
						</fo:table-cell>
						<fo:table-cell space-start="0.25cm">
							<fo:block font-weight="bold">
							Sello Digital del CFDI:
							</fo:block>
							<fo:block space-after="0.25cm">
								<xsl:call-template name="intersperse-with-zero-spaces">
									<xsl:with-param name="str" select="tfd:TimbreFiscalDigital/@selloCFD"/>
								</xsl:call-template>
							</fo:block>
							<fo:block font-weight="bold">
							Sello del SAT:
							</fo:block>
							<fo:block space-after="0.25cm">
								<xsl:call-template name="intersperse-with-zero-spaces">
									<xsl:with-param name="str" select="tfd:TimbreFiscalDigital/@selloSAT"/>
								</xsl:call-template>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
			<!--fo:block font-weight="bold" space-before="0.5cm">Cadena original del complemento de certificación digital del SAT: </fo:block-->
		</fo:block>
	</xsl:template>

	<xsl:template name="QRCode">
		<xsl:param name="str"/>
		<fo:block>
			<fo:instream-foreign-object>
				<qr:qrcode length="40" cellsize="8" margin="5" type="9" correction="H" message='{$str}'/>
			</fo:instream-foreign-object>
		</fo:block>
	</xsl:template>

	<xsl:template name="intersperse-with-zero-spaces">
		<xsl:param name="str"/>
		<xsl:variable name="spacechars">
        &#x9;&#xA;
        &#x2000;&#x2001;&#x2002;&#x2003;&#x2004;&#x2005;
        &#x2006;&#x2007;&#x2008;&#x2009;&#x200A;&#x200B;
		</xsl:variable>

		<xsl:if test="string-length($str) &gt; 0">
			<xsl:variable name="c1" select="substring($str, 1, 1)"/>
			<xsl:variable name="c2" select="substring($str, 2, 1)"/>

			<xsl:value-of select="$c1"/>
			<xsl:if test="$c2 != '' and
            not(contains($spacechars, $c1) or
            contains($spacechars, $c2))">
				<xsl:text>&#x200B;</xsl:text>
			</xsl:if>

			<xsl:call-template name="intersperse-with-zero-spaces">
				<xsl:with-param name="str" select="substring($str, 2)"/>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>