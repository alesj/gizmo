/*
 * Copyright 2018 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.quarkus.gizmo;

import org.junit.Test;
import org.objectweb.asm.Opcodes;

import static org.junit.Assert.assertEquals;

public class ArithmeticTestCase {

    @Test
    public void testAddition() throws Exception {
        TestClassLoader cl = new TestClassLoader(getClass().getClassLoader());
        try (ClassCreator creator = ClassCreator.builder().classOutput(cl).className("com.MyTest").build()) {
            MethodCreator addInts = creator.getMethodCreator("addInts", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle int1 = addInts.load(1);
            ResultHandle int2 = addInts.load(2);
            addInts.returnValue(addInts.add(int1, int2));

            MethodCreator addLongs = creator.getMethodCreator("addLongs", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle long1 = addLongs.load(3L);
            ResultHandle long2 = addLongs.load(4L);
            addLongs.returnValue(addLongs.add(long1, long2));

            MethodCreator addFloats = creator.getMethodCreator("addFloats", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle float1 = addFloats.load(5.0F);
            ResultHandle float2 = addFloats.load(6.0F);
            addFloats.returnValue(addFloats.add(float1, float2));

            MethodCreator addDoubles = creator.getMethodCreator("addDoubles", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle double1 = addDoubles.load(7.0);
            ResultHandle double2 = addDoubles.load(8.0);
            addDoubles.returnValue(addDoubles.add(double1, double2));
        }

        Class<?> clazz = cl.loadClass("com.MyTest");
        assertEquals(3, clazz.getMethod("addInts").invoke(null));
        assertEquals(7L, clazz.getMethod("addLongs").invoke(null));
        assertEquals(11.0F, clazz.getMethod("addFloats").invoke(null));
        assertEquals(15.0, clazz.getMethod("addDoubles").invoke(null));
    }

    @Test
    public void testMultiplication() throws Exception {
        TestClassLoader cl = new TestClassLoader(getClass().getClassLoader());
        try (ClassCreator creator = ClassCreator.builder().classOutput(cl).className("com.MyTest").build()) {
            MethodCreator multiplyInts = creator.getMethodCreator("multiplyInts", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle int1 = multiplyInts.load(2);
            ResultHandle int2 = multiplyInts.load(3);
            multiplyInts.returnValue(multiplyInts.multiply(int1, int2));

            MethodCreator multiplyLongs = creator.getMethodCreator("multiplyLongs", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle long1 = multiplyLongs.load(4L);
            ResultHandle long2 = multiplyLongs.load(5L);
            multiplyLongs.returnValue(multiplyLongs.multiply(long1, long2));

            MethodCreator multiplyFloats = creator.getMethodCreator("multiplyFloats", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle float1 = multiplyLongs.load(6.0F);
            ResultHandle float2 = multiplyLongs.load(7.0F);
            multiplyFloats.returnValue(multiplyFloats.multiply(float1, float2));

            MethodCreator multiplyDoubles = creator.getMethodCreator("multiplyDoubles", Object.class).setModifiers(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC);
            ResultHandle double1 = multiplyLongs.load(8.0F);
            ResultHandle double2 = multiplyLongs.load(9.0F);
            multiplyDoubles.returnValue(multiplyDoubles.multiply(double1, double2));
        }
        Class<?> clazz = cl.loadClass("com.MyTest");
        assertEquals(6, clazz.getMethod("multiplyInts").invoke(null));
        assertEquals(20L, clazz.getMethod("multiplyLongs").invoke(null));
        assertEquals(42.0F, clazz.getMethod("multiplyFloats").invoke(null));
        assertEquals(72.0F, clazz.getMethod("multiplyDoubles").invoke(null));
    }
}
