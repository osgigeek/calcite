/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.calcite.sql.type;

import org.apache.calcite.rel.type.RelDataTypeComparability;
import org.apache.calcite.rel.type.RelDataTypeFamily;
import org.apache.calcite.rel.type.RelDataTypeField;

import org.apache.calcite.rel.type.StructKind;

import org.apache.calcite.sql.SqlIdentifier;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

public class VariantSqlType extends AbstractSqlType{

  private final @Nullable SqlIdentifier sqlIdentifier;

  private final RelDataTypeComparability comparability;

  private @Nullable RelDataTypeFamily family;

  /**
   * Creates an AbstractSqlType.
   *
   * @param typeName   Type name
   * @param isNullable Whether nullable
   * @param fields     Fields of type, or null if not a record type
   */
  protected VariantSqlType(SqlTypeName typeName,
      @Nullable SqlIdentifier sqlIdentifier,
      boolean isNullable,
      @Nullable List<?extends RelDataTypeField> fields,
      RelDataTypeComparability comparability) {
    super(SqlTypeName.VARIANT, isNullable, fields);
    this.sqlIdentifier = sqlIdentifier;
    this.comparability = comparability;
    computeDigest();
  }

  @Override
  public StructKind getStructKind() {
    return StructKind.FULLY_QUALIFIED;
  }

  public void setFamily(RelDataTypeFamily family) {
    this.family = family;
  }

  @Override public RelDataTypeFamily getFamily() {
    // each UDT is in its own lonely family, until one day when
    // we support inheritance (at which time also need to implement
    // getPrecedenceList).
    RelDataTypeFamily family = this.family;
    return family != null ? family : this;
  }

  @Override public @Nullable SqlIdentifier getSqlIdentifier() {
    return sqlIdentifier;
  }

  @Override
  protected void generateTypeString(StringBuilder sb, boolean withDetail) {
    sb.append("VariantSqlType(");
    sb.append(sqlIdentifier);
    sb.append(")");
  }
}
