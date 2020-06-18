import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class AddressEdit extends Component {

  emptyItem = {
    street: '',
    city:'',
    state:'',
    postalCode:''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.id !== 'new') {
      const person = await (await fetch(`/api/person/address/${this.props.match.params.id}`)).json();
      this.setState({item: person});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/person/'+this.props.match.params.personid+'/address', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/people');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Address' : 'Add Address'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="name">Street</Label>
            <Input type="text" name="street" id="street" value={item.street || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
           <FormGroup>
            <Label for="name">City</Label>
            <Input type="text" name="city" id="city" value={item.city || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="name">State</Label>
            <Input type="text" name="state" id="state" value={item.state || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
          <FormGroup>
            <Label for="name">Postal Code</Label>
            <Input type="text" name="postalCode" id="postalCode" value={item.postalCode || ''}
                   onChange={this.handleChange} autoComplete="name"/>
          </FormGroup>
   
       
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/people">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(AddressEdit);