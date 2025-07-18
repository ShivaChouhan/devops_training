terraform {

  required_version = ">= 1.3.0"
 
  required_providers {

    aws = {

      source  = "hashicorp/aws"

      version = "~> 5.42.0"

    }

  }

}
 
#provider "aws" {

#  region = var.aws_region

#}
 
# VPC Module

module "vpc" {

  source  = "terraform-aws-modules/vpc/aws"

  version = "5.1.2"
 
  name = "${var.project_name}-vpc"

  cidr = "10.0.0.0/16"
 
  azs             = ["us-east-1a", "us-east-1b"]

  private_subnets = ["10.0.1.0/24", "10.0.2.0/24"]

  public_subnets  = ["10.0.101.0/24", "10.0.102.0/24"]
 
  enable_nat_gateway = true

  single_nat_gateway = true
 
  tags = {

    Terraform = "true"

    Environment = "dev"

  }

}
 
# EKS Module

module "eks" {

  source          = "terraform-aws-modules/eks/aws"

  version         = "20.8.3"
 
  cluster_name    = "${var.project_name}-eks"

  cluster_version = "1.27"

  subnet_ids      = module.vpc.private_subnets

  vpc_id          = module.vpc.vpc_id
 
  eks_managed_node_groups = {

    default = {

      instance_types = ["t3.medium"]

      min_size       = 1

      max_size       = 2

      desired_size   = 1

    }

  }
 
  tags = {

    Environment = "dev"

    Terraform   = "true"

  }

}
 
# ECS Cluster

resource "aws_ecs_cluster" "this" {

  name = "${var.project_name}-ecs"

}
 
# S3 Bucket

resource "aws_s3_bucket" "this" {

  bucket        = "${var.project_name}-bucket-by-terraform"

  force_destroy = true
 
  tags = {

    Environment = "dev"

    Terraform   = "true"

  }

}
 
# RDS (MySQL)

resource "random_password" "db_password" {
  length           = 16
  special          = true
  override_special = "!#$%&*()-_=+[]{}<>:?"
}

resource "aws_db_instance" "this" {

  allocated_storage    = 20

  engine               = "mysql"

  engine_version       = "8.0"

  instance_class       = "db.t3.micro"

  db_name                 = "demo"

  username             = "admin"

#  password             = "password123"

   password            = random_password.db_password.result   

  skip_final_snapshot  = true

  db_subnet_group_name = aws_db_subnet_group.this.name

  vpc_security_group_ids = [aws_security_group.rds_sg.id]

}
 
resource "aws_db_subnet_group" "this" {

  name       = "rds-subnet-group"

  subnet_ids = module.vpc.private_subnets
 
  tags = {

    Name = "My DB subnet group"

  }

}
 
resource "aws_security_group" "rds_sg" {

  name   = "rds-sg"

  vpc_id = module.vpc.vpc_id
 
  ingress {

    from_port   = 3306

    to_port     = 3306

    protocol    = "tcp"

    cidr_blocks = ["0.0.0.0/0"]

  }
 
  egress {

    from_port   = 0

    to_port     = 0

    protocol    = "-1"

    cidr_blocks = ["0.0.0.0/0"]

  }

}

 