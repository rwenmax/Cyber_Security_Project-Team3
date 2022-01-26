#Association of route tables to Subnet
resource "aws_route_table_association" "java10x_cyberg3_rt_assoc_app_tf" {
  subnet_id = "${var.var_subnet_app_id_tf}"
  route_table_id = "${var.var_route_table_id_tf}"
}

#App NACL
resource "aws_network_acl" "java10x_cyberg3_nacl-app" {
  vpc_id = "${var.var_vpc_id_tf}"

  ingress {
    protocol = "tcp"
    rule_no = 100
    action = "allow"
    cidr_block = "${var.var_client_ip_address_tf}"
    from_port = 22
    to_port = 22
  }

  ingress {
    protocol = "tcp"
    rule_no = 200
    action = "allow"
    cidr_block = "10.3.4.0/24"
    from_port = 8080
    to_port = 8080
  }

  ingress {
    protocol = "tcp"
    rule_no = 300
    action = "allow"
    cidr_block = "10.3.4.0/24"
    from_port = 80
    to_port = 80
  }

  ingress {
    protocol = "tcp"
    rule_no = 400
    action = "allow"
    cidr_block = "10.3.4.0/24"
    from_port = 443
    to_port = 443
  }
#Unsure about this
  ingress {
    protocol = "tcp"
    rule_no = 500
    action = "allow"
    cidr_block = "0.0.0.0/0"
    from_port = 1024
    to_port = 65535
    }

    egress {
      protocol = "tcp"
      rule_no = 100
      action = "allow"
      cidr_block = "10.3.2.0/24"
      from_port = 3306
      to_port = 3306
    }

    egress {
      protocol = "tcp"
      rule_no = 300
      action = "allow"
      cidr_block = "0.0.0.0/0"
      from_port = 80
      to_port = 80
    }

    egress {
      protocol = "tcp"
      rule_no = 200
      action = "allow"
      cidr_block = "0.0.0.0/0"
      from_port = 443
      to_port = 443
    }
    egress {
      protocol = "tcp"
      rule_no = 400
      action = "allow"
      cidr_block = "0.0.0.0/0"
      from_port = 8080
      to_port = 8080
    }

    egress {
      protocol = "tcp"
      rule_no = 1000
      action = "allow"
      cidr_block = "0.0.0.0/0"
      from_port = 1024
      to_port = 65535
    }

    subnet_ids = ["${var.var_subnet_app_id_tf}"]

  tags = {
    Name = "java10x_cyberg3_nacl_app"
  }
}
