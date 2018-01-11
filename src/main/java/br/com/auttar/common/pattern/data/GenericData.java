package br.com.auttar.common.pattern.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonInclude(Include.NON_NULL)
public abstract class GenericData<T> {

    public abstract String getId();
    public abstract void setId(String id);
}
