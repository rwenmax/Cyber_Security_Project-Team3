#Association of route tables to Subnet
resource "aws_route_table_association" "java10x_sakila_plalji_rt_assoc_proxy_tf" {
  subnet_id = "${var.var_subnet_proxy_id_tf}"
  route_table_id = "${var.var_route_table_id_tf}"
}
