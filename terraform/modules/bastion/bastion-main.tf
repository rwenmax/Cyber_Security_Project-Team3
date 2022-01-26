#Association of route tables to Subnet
resource "aws_route_table_association" "java10x_cyberg3_rt_assoc_bastion_tf" {
  subnet_id = "${var.var_subnet_bastion_id_tf}"
  route_table_id = "${var.var_route_table_id_tf}"
}



#Bastion Subnet NACL
resource "aws_network_acl" "java10x_cyberg3_nacl-bastion" {
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
    cidr_block = "10.3.2.0/24"
    from_port = 1024
    to_port = 65535
  }


    egress {
      protocol = "tcp"
      rule_no = 100
      action = "allow"
      cidr_block = "10.3.2.0/24"
      from_port = 22
      to_port = 22
    }

    egress {
      protocol = "tcp"
      rule_no = 1000
      action = "allow"
      cidr_block = "0.0.0.0/0"
      from_port = 1024
      to_port = 65535
    }

    subnet_ids = ["${var.var_subnet_bastion_id_tf}"]

    tags = {
      Name = "java10x_cyberg3_nacl_bastion"
    }
}


#Security Group of Bastion Subnet
resource "aws_security_group" "java10x_cyberg3_sg_bastion_tf" {
  name = "java10x_cyberg3_sg_bastion"
  vpc_id = "${var.var_vpc_id_tf}"
  ingress {
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["${var.var_client_ip_address_tf}"]
  }
  egress {
    protocol = "tcp"
    from_port = 22
    to_port = 22
    cidr_blocks = ["10.3.2.0/24"]
  }
  egress {
    protocol = "tcp"
    from_port = 443
    to_port = 443
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    protocol = "tcp"
    from_port = 80
    to_port = 80
    cidr_blocks = ["0.0.0.0/0"]
  }
  tags = {
    Name = "java10x_cyberg3_sg_bastion"
  }
}


#Bastion Instance
resource "aws_instance" "java10x_cyberg3_bastion_tf" {
  ami = "${var.var_ami_app_server_tf}"
  instance_type = "t2.micro"
  key_name =  "${var.var_global_key_name_tf}"
  subnet_id = "${var.var_subnet_bastion_id_tf}"
  vpc_security_group_ids = [aws_security_group.java10x_cyberg3_sg_bastion_tf.id]
  associate_public_ip_address = true
  depends_on = [var.var_depends_on_database]
  tags = {
    Name = "java10x_cyberg3_bastion"
  }
}


#Route for database
resource "aws_route53_record" "java10x_cyberg3_r53_record_db_tf" {
  zone_id = "${var.var_route53_zone_id_tf}"
  name = "bastion"
  type = "A"
  ttl = "30"
  records = [aws_instance.java10x_cyberg3_bastion_tf.private_ip]
}