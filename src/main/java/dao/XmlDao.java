package dao;

import exceptions.ReadWriteException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;

public class XmlDao<T> extends FileDao<T> {
    public XmlDao(String filename, Class<T> clazz) {
        super(filename, clazz);
    }

    public XmlDao() { }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> read() throws ReadWriteException {
        try {
            JAXBContext context = JAXBContext.newInstance(Data.class, clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            var events = (Data<T>) unmarshaller.unmarshal(new File(filename));
            return events.getSportEvents();
        } catch (JAXBException e) {
            throw new ReadWriteException(e);
        }
    }

    public void write(List<T> data) throws ReadWriteException{
        try {
            var arr = new Data<>(data);
            JAXBContext context = JAXBContext.newInstance(Data.class, clazz);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            m.marshal(arr, new File(filename));
        } catch (JAXBException e) {
            throw new ReadWriteException(e);
        }
    }
}

@XmlRootElement(name = "data")
@XmlAccessorType(XmlAccessType.FIELD)
class Data<T> {

    @XmlElement(name = "elem")
    private List<T> data;

    public Data() {}

    public Data(List<T> data) {
        this.data = data;
    }

    public List<T> getSportEvents() {
        return data;
    }

    public void setSportEvents(List<T> data) {
        this.data = data;
    }
}
