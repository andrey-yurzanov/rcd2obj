/*
 *    Copyright 2024 Andrey Yurzanov
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package dev.evilbrainstudio.rcd2obj.codegen;

/**
 * This exception signals about problems of rendering.
 *
 * @author Andrey_Yurzanov
 */
public class JavaElementRenderingException extends RuntimeException {
  private static final char TEMPLATE_SYMBOL = '$';

  /**
   * It constructs new instance of the exception without a message.
   */
  public JavaElementRenderingException() {
    super();
  }

  /**
   * It constructs new instance of the exception with a message.
   *
   * <pre>
   * Example:
   *
   * {@code
   * new JavaElementRenderingException("Parameter has incorrect value: $!", "incorrect")
   * }
   *
   * Final message: Parameter has incorrect value: incorrect!
   * </pre>
   *
   * @param message error message
   * @param args    arguments of error message
   * @throws IllegalArgumentException it throws when message contains to many parameters
   */
  public JavaElementRenderingException(String message, Object... args) throws IllegalArgumentException {
    super(parseMessage(message, args));
  }

  private static String parseMessage(String message, Object... args) {
    StringBuilder buffer = new StringBuilder();

    int argIndex = 0;
    for (char symbol : message.toCharArray()) {
      if (symbol == TEMPLATE_SYMBOL) {
        if (argIndex >= args.length) {
          throw new IllegalArgumentException("Message contains too many parameters!");
        }

        buffer.append(args[argIndex]);
        argIndex++;
      } else {
        buffer.append(symbol);
      }
    }
    return buffer.toString();
  }
}
