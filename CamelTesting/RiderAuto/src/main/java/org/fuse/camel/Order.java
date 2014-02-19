package org.fuse.camel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@CsvRecord(separator = ",", skipFirstLine = true)
public class Order implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6464698749629684447L;

	@XmlAttribute
    @DataField(pos = 1)
    private String name;

    @XmlAttribute
    @DataField(pos = 2)
    private int amount;
 
   public Order() {
    }

    public Order(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
}
