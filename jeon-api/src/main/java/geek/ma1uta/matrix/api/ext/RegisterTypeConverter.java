package geek.ma1uta.matrix.api.ext;

import geek.ma1uta.matrix.model.RegisterType;

import javax.ws.rs.ext.ParamConverter;

public class RegisterTypeConverter implements ParamConverter<RegisterType> {
    @Override
    public RegisterType fromString(String value) {
        return RegisterType.valueOf(value);
    }

    @Override
    public String toString(RegisterType value) {
        return value.code();
    }
}
