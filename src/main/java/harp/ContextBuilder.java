/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package harp;

import com.google.common.base.Preconditions;
import harp.definitions.Executable;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public final class ContextBuilder {

  private final List<Executable> executables;

  ContextBuilder() {
    executables = new ArrayList<Executable>();
  }

  public void addExecutable(Executable executable) {
    Preconditions.checkArgument(
        !executables.contains(executable),
        "This executable has already been declared!");
    executables.add(executable);
  }

  public Context build() {
    return new Context(executables);
  }
}
