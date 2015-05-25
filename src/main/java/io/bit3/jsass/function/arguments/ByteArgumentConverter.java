package io.bit3.jsass.function.arguments;

import io.bit3.jsass.context.Context;
import io.bit3.jsass.function.FunctionArgumentSignature;
import io.bit3.jsass.function.FunctionArgumentSignatureFactory;
import io.bit3.jsass.importer.Import;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import io.bit3.jsass.context.ImportStack;

public class ByteArgumentConverter implements ArgumentConverter {

  @Override
  public Object convert(
      List<?> remainingArguments, ImportStack importStack, Context context
  ) {
    if (remainingArguments.isEmpty()) {
      return null;
    }

    Object value = remainingArguments.remove(0);

    // ignore null value
    if (null == value) {
      return null;
    }

    // value is already in a compatible type
    if (
        Byte.class.isAssignableFrom(value.getClass())
        || byte.class.isAssignableFrom(value.getClass())
        ) {
      return value;
    }

    if (value instanceof Number) {
      value = ((Number) value).byteValue();
    } else {
      value = Byte.parseByte(value.toString());
    }

    return value;
  }

  @Override
  public List<FunctionArgumentSignature> argumentSignatures(
      Object object, Method method, Parameter parameter, FunctionArgumentSignatureFactory factory
  ) {
    return factory.createDefaultArgumentSignature(method, parameter);
  }
}