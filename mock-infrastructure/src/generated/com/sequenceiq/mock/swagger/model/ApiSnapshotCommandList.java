package com.sequenceiq.mock.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.sequenceiq.mock.swagger.model.ApiListBase;
import com.sequenceiq.mock.swagger.model.ApiSnapshotCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * A list of snapshot commands.
 */
@ApiModel(description = "A list of snapshot commands.")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2021-12-10T21:24:30.629+01:00")




public class ApiSnapshotCommandList extends ApiListBase  {
  @JsonProperty("items")
  @Valid
  private List<ApiSnapshotCommand> items = null;

  public ApiSnapshotCommandList items(List<ApiSnapshotCommand> items) {
    this.items = items;
    return this;
  }

  public ApiSnapshotCommandList addItemsItem(ApiSnapshotCommand itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * 
   * @return items
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<ApiSnapshotCommand> getItems() {
    return items;
  }

  public void setItems(List<ApiSnapshotCommand> items) {
    this.items = items;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiSnapshotCommandList apiSnapshotCommandList = (ApiSnapshotCommandList) o;
    return Objects.equals(this.items, apiSnapshotCommandList.items) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(items, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiSnapshotCommandList {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

